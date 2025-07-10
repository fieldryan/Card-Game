public record Card(String id, int value) {
    @Override
    public String toString() {
        return id;
    }
}