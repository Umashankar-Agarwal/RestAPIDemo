public class JsonPayloads {

    public static String createOrderPayload(String productId){
        return "{\n" +
                "    \"orders\": [\n" +
                "        {\n" +
                "            \"country\": \"India\",\n" +
                "            \"productOrderedId\": \""+productId+"\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
    }
}
