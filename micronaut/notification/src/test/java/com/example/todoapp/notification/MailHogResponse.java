package com.example.todoapp.notification;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

import java.util.List;
import java.util.Map;

@Serdeable
public class MailHogResponse {

    private int total;
    private int count;
    private int start;
    private List<Item> items;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Serdeable
    public static class Item {
        @JsonProperty("ID")
        private String id;
        @JsonProperty("From")
        private From from;
        @JsonProperty("To")
        private List<To> to;
        @JsonProperty("Content")
        private Content content;
        @JsonProperty("Created")
        private String created;
        @JsonProperty("MIME")
        private MIME mime;
        @JsonProperty("Raw")
        private Raw raw;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public From getFrom() {
            return from;
        }

        public void setFrom(From from) {
            this.from = from;
        }

        public List<To> getTo() {
            return to;
        }

        public void setTo(List<To> to) {
            this.to = to;
        }

        public Content getContent() {
            return content;
        }

        public void setContent(Content content) {
            this.content = content;
        }

        public String getCreated() {
            return created;
        }

        public void setCreated(String created) {
            this.created = created;
        }

        public MIME getMime() {
            return mime;
        }

        public void setMime(MIME mime) {
            this.mime = mime;
        }

        public Raw getRaw() {
            return raw;
        }

        public void setRaw(Raw raw) {
            this.raw = raw;
        }

        @Serdeable
        public static class From {
            @JsonProperty("Relays")
            private List<String> relays;
            @JsonProperty("Mailbox")
            private String mailbox;
            @JsonProperty("Domain")
            private String domain;
            @JsonProperty("Params")
            private String params;

            public List<String> getRelays() {
                return relays;
            }

            public void setRelays(List<String> relays) {
                this.relays = relays;
            }

            public String getMailbox() {
                return mailbox;
            }

            public void setMailbox(String mailbox) {
                this.mailbox = mailbox;
            }

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }

            public String getParams() {
                return params;
            }

            public void setParams(String params) {
                this.params = params;
            }
        }

        @Serdeable
        public static class To {
            @JsonProperty("Relays")
            private List<String> relays;
            @JsonProperty("Mailbox")
            private String mailbox;
            @JsonProperty("Domain")
            private String domain;
            @JsonProperty("Params")
            private String params;

            public List<String> getRelays() {
                return relays;
            }

            public void setRelays(List<String> relays) {
                this.relays = relays;
            }

            public String getMailbox() {
                return mailbox;
            }

            public void setMailbox(String mailbox) {
                this.mailbox = mailbox;
            }

            public String getDomain() {
                return domain;
            }

            public void setDomain(String domain) {
                this.domain = domain;
            }

            public String getParams() {
                return params;
            }

            public void setParams(String params) {
                this.params = params;
            }
        }

        @Serdeable
        public static class Content {
            @JsonProperty("Headers")
            private Map<String, List<String>> headers;
            @JsonProperty("Body")
            private String body;
            @JsonProperty("Size")
            private int size;
            @JsonProperty("MIME")
            private MIME mime;

            public Map<String, List<String>> getHeaders() {
                return headers;
            }

            public void setHeaders(Map<String, List<String>> headers) {
                this.headers = headers;
            }

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public MIME getMime() {
                return mime;
            }

            public void setMime(MIME mime) {
                this.mime = mime;
            }
        }

        @Serdeable
        public static class MIME {
            @JsonProperty("Parts")
            private List<Part> parts;

            public List<Part> getParts() {
                return parts;
            }

            public void setParts(List<Part> parts) {
                this.parts = parts;
            }

            @Serdeable
            public static class Part {
                @JsonProperty("Headers")
                private Map<String, List<String>> headers;
                @JsonProperty("Body")
                private String body;
                @JsonProperty("Size")
                private int size;
                @JsonProperty("MIME")
                private MIME mime;

                public Map<String, List<String>> getHeaders() {
                    return headers;
                }

                public void setHeaders(Map<String, List<String>> headers) {
                    this.headers = headers;
                }

                public String getBody() {
                    return body;
                }

                public void setBody(String body) {
                    this.body = body;
                }

                public int getSize() {
                    return size;
                }

                public void setSize(int size) {
                    this.size = size;
                }

                public MIME getMime() {
                    return mime;
                }

                public void setMime(MIME mime) {
                    this.mime = mime;
                }
            }
        }

        @Serdeable
        public static class Raw {
            @JsonProperty("From")
            private String from;
            @JsonProperty("To")
            private List<String> to;
            @JsonProperty("Data")
            private String data;
            @JsonProperty("Helo")
            private String helo;

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public List<String> getTo() {
                return to;
            }

            public void setTo(List<String> to) {
                this.to = to;
            }

            public String getData() {
                return data;
            }

            public void setData(String data) {
                this.data = data;
            }

            public String getHelo() {
                return helo;
            }

            public void setHelo(String helo) {
                this.helo = helo;
            }
        }
    }
}