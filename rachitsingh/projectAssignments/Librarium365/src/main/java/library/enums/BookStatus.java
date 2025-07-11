package library.enums;

public enum BookStatus {
    ACTIVE("A", "Active"),
    INACTIVE("I", "Inactive");

    private final String code;
    private final String label;

    BookStatus(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    public static BookStatus fromLabel(String label) {
        for (BookStatus status : BookStatus.values()) {
            if (status.getLabel().equalsIgnoreCase(label)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown book status label: " + label);
    }

    public static BookStatus fromCode(String code) {
        for (BookStatus status : BookStatus.values()) {
            if (status.getCode().equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown book status code: " + code);
    }
}
