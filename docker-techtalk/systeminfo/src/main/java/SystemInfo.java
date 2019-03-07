import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.JMException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
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

	@RequestMapping(value = "/die", produces = { "text/plain"})
	String die() throws JMException, UnknownHostException {
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
}
