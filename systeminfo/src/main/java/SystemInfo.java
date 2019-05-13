import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@RestController
@EnableAutoConfiguration
public class SystemInfo {

	public static final String READINESS_CHECK_FILE = "/tmp/unhealthy";
	public static final String LIVENESS_CHECK_FILE = "/tmp/failing";

	@RequestMapping(value = "/die", produces = { "text/plain"})
	void die() {
		System.exit(85);
	}

	@RequestMapping(value = "/memory", produces = { "text/plain"})
	String memory() {
		List<byte[]> mem = new ArrayList<>();
		int iteration = 0;
		Runtime rt = Runtime.getRuntime();
		while (true) {
			iteration++;
			byte b[] = new byte[1048576];
			mem.add(b);

			if(iteration % 10 == 0) {
				System.out.print("Free Memory (iteration: "+iteration+"): " + new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.JVM, SystemInfoEntry.ValueType.LONG_BYTES, rt.freeMemory(), "Free Heap"));
			}
		}
	}

	@RequestMapping(value = "/ready", produces = { "text/plain"})
	String ready(HttpServletResponse response) {
		String rval = "readiness: im ok";
		response.setStatus(200);
		if(!isReady()) {
			response.setStatus(500);
			rval = "readiness: unhealthy";
		}

		return rval;
	}

	@RequestMapping(value = "/live", produces = { "text/plain"})
	String live(HttpServletResponse response) {
		String rval = "liveness: im ok";
		response.setStatus(200);
		if(!isAlive()) {
			response.setStatus(500);
			rval = "liveness: failing";
		}

		return rval;
	}

	@RequestMapping(value = "/set/unhealthy", produces = { "text/plain"})
	String setUnhealthy() throws IOException {
		File f = new File(READINESS_CHECK_FILE);
		f.createNewFile();

		return "readiness: unhealthy";
	}

	@RequestMapping(value = "/clear/unhealthy", produces = { "text/plain"})
	String setHealthy() {
		File f = new File(READINESS_CHECK_FILE);
		if(f.exists()) {
			f.delete();
		}

		return "readiness: healthy";
	}

	@RequestMapping(value = "/set/failing", produces = { "text/plain"})
	String setFailing() throws IOException {
		File fHealth = new File(READINESS_CHECK_FILE);
		fHealth.createNewFile();

		File fFailing = new File(LIVENESS_CHECK_FILE);
		fFailing.createNewFile();

		return "liveness: failing";
	}

	@RequestMapping(value = "/clear/failing", produces = { "text/plain"})
	String setLive() {
		File f = new File(LIVENESS_CHECK_FILE);
		if(f.exists()) {
			f.delete();
		}

		return "liveness: alive";
	}

	@RequestMapping(value = "/", produces = { "text/plain"})
	String info(HttpServletRequest request) throws JMException, UnknownHostException {
		ArrayList<SystemInfoEntry> entries = new ArrayList<>();

		Runtime runtime = Runtime.getRuntime();
		long maxHeap = runtime.maxMemory();
		long currentHeap = runtime.totalMemory();
		long freeHeap = runtime.freeMemory();
		int availableProcessors = runtime.availableProcessors();
		String javaVersion = System.getProperty("java.version");
		String osName = System.getProperty("os.name");
		String osVersion = System.getProperty("os.version");

		String userAgent = request.getHeader("User-Agent");
		String clientIp = getClientIpAddress(request);

		InetAddress address = InetAddress.getLocalHost();
		String ip = address.getHostAddress();
		String hostname = address.getHostName();

		RuntimeMXBean mxBean = ManagementFactory.getRuntimeMXBean();
		String jvmArgs = String.join(" ", mxBean.getInputArguments());
		String vmVersion = mxBean.getVmVersion();

		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		Object totalPhysicalMemory = mBeanServer.getAttribute(new ObjectName("java.lang","type","OperatingSystem"), "TotalPhysicalMemorySize");

		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.REQ, SystemInfoEntry.Target.CLIENT, SystemInfoEntry.ValueType.STRING, userAgent, "User-Agent"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.REQ, SystemInfoEntry.Target.CLIENT, SystemInfoEntry.ValueType.STRING_LIMITED, clientIp, "Client-IP"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.SYSTEM, SystemInfoEntry.ValueType.STRING_LIMITED, ip, "IP"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.SYSTEM, SystemInfoEntry.ValueType.STRING_LIMITED, hostname, "Hostname"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.SYSTEM, SystemInfoEntry.ValueType.STRING_BYTES, totalPhysicalMemory.toString(), "Physical memory"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.JVM, SystemInfoEntry.ValueType.LONG_BYTES, maxHeap, "Max Heap (-Xmx)"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.JVM, SystemInfoEntry.ValueType.LONG_BYTES, currentHeap, "Current Heap"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.JVM, SystemInfoEntry.ValueType.LONG_BYTES, freeHeap, "Free Heap"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.JVM, SystemInfoEntry.ValueType.INT, availableProcessors, "Available processors (cores)"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.SYSTEM, SystemInfoEntry.ValueType.STRING_LIMITED, osName, "OS Name"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.SYSTEM, SystemInfoEntry.ValueType.STRING_LIMITED, osVersion, "Kernel Version"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.JVM, SystemInfoEntry.ValueType.STRING_LIMITED, javaVersion, "Java Version"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.JVM, SystemInfoEntry.ValueType.STRING_LIMITED, vmVersion, "VM Version"));

		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.JVM, SystemInfoEntry.ValueType.STRING, mxBean.getVmVendor(), "VM Vendor"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.JVM, SystemInfoEntry.ValueType.STRING, mxBean.getVmName(), "VM Name"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.JVM, SystemInfoEntry.ValueType.STRING, mxBean.getVmVersion(), "VM Version"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.JAVA, SystemInfoEntry.Target.JVM, SystemInfoEntry.ValueType.STRING, jvmArgs, "JVM Args"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.ENV, SystemInfoEntry.Target.JVM, SystemInfoEntry.ValueType.STRING, System.getenv("JAVA_OPTS"), "JAVA_OPTS"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.APP, SystemInfoEntry.Target.APP, SystemInfoEntry.ValueType.STRING, isReady(), "Is ready (/ready)"));
		entries.add(new SystemInfoEntry(SystemInfoEntry.Source.APP, SystemInfoEntry.Target.APP, SystemInfoEntry.ValueType.STRING, isAlive(), "Is alive (/live)"));

		return buildString(entries);
	}

	public static String getClientIpAddress(HttpServletRequest request) {
		String xForwardedForHeader = request.getHeader("X-Forwarded-For");
		if (xForwardedForHeader == null) {
			return request.getRemoteAddr();
		} else {
			// As of https://en.wikipedia.org/wiki/X-Forwarded-For
			// The general format of the field is: X-Forwarded-For: client, proxy1, proxy2 ...
			// we only want the client
			return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
		}
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SystemInfo.class, args);
	}

	private String buildString(ArrayList<SystemInfoEntry> entries) {
		return entries.stream().map(SystemInfoEntry::toString).collect(Collectors.joining());
	}

	private boolean isReady() {
		File f = new File(READINESS_CHECK_FILE);
		return !f.exists();
	}

	private boolean isAlive() {
		File f = new File(LIVENESS_CHECK_FILE);
		return !f.exists();
	}
}
