public class SystemInfoEntry {

    public enum Source { JAVA, ENV, SYSTEM, REQ, APP };
    public enum Target { JVM, SYSTEM, CLIENT, APP };
    public enum ValueType { LONG_BYTES, STRING_BYTES, STRING_LIMITED, STRING, INT, SEPARATOR }

    private Source source;
    private Target target;
    private ValueType type;
    private Object value;
    private String description;

    public SystemInfoEntry(Source source, Target target, ValueType type, Object value) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.value = value;
        this.description = null;
    }

    public SystemInfoEntry(Source source, Target target, ValueType type, Object value, String description) {
        this.source = source;
        this.target = target;
        this.type = type;
        this.value = value;
        this.description = description;
    }

    private String human(long bytes) {
        return human(bytes, false);
    }

    private String human(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }

    @Override
    public String toString() {
        switch (type) {
            case LONG_BYTES:
                return String.format("%-6s %-12s %-15s (%15d bytes) %s%n", source.toString(), target.toString(), human((Long)value), (Long)value, description != null ? description : "");
            case STRING_BYTES:
                long bytes = Long.parseLong(value.toString());
                return String.format("%-6s %-12s %-15s (%15d bytes) %s%n", source.toString(), target.toString(), human(bytes), bytes, description != null ? description : "");
            case INT:
                return String.format("%-6s %-12s %-15d %23s %s%n", source.toString(), target.toString(), (Integer)value, "", description != null ? description : "");
            case STRING_LIMITED:
                return String.format("%-6s %-12s %-15s %s%n", source.toString(), target.toString(), value != null ? value.toString() : "-", description != null ? description : "");
            case STRING:
                String sval = value != null ? value.toString() : "-";
                String value = (description != null) ? description +": " + sval : sval;
                return String.format("%-6s %-12s %s%n", source.toString(), target.toString(), value);
            default:
                return String.format("%n");
        }
    }
}
