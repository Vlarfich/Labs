package com.epam.rd.contactbook;

public class Contact {

    private String name;
    private ContactInfo phone;
    private Email[] emails = new Email[3];
    private int pos1 = 0;
    private Social[] socials = new Social[5];
    private int pos2 = 0;

    public Contact(String contactName) {
        name = contactName;
    }

    public void rename(String newName) {
        if (newName != null && newName != "")
            name = newName;
    }

    public Email addEmail(String localPart, String domain) {
        if (pos1 == emails.length)
            return null;
        emails[pos1++] = new Email(localPart + "@" + domain);
        return emails[pos1 - 1];
    }


    public Email addEpamEmail(String firstname, String lastname) {
        if (pos1 == emails.length)
            return null;
        emails[pos1] = new Email(firstname + "_" + lastname + "@epam.com") {
            @Override
            public String getTitle() {
                return "Epam Email";
            }

            @Override
            public String getValue() {
                return value;
            }
        };
        pos1++;
        return emails[pos1 - 1];
    }

    public ContactInfo addPhoneNumber(int code, String number) {
        if (phone != null)
            return null;
        phone = new ContactInfo() {
            @Override
            public String getTitle() {
                return "Tel";
            }

            @Override
            public String getValue() {
                return "+" + code + " " + number;
            }
        };
        return phone;
    }

    public Social addTwitter(String twitterId) {
        if (pos2 == socials.length)
            return null;
        socials[pos2] = new Social(twitterId, "Twitter");
        return socials[pos2++];
    }

    public Social addInstagram(String instagramId) {
        if (pos2 == socials.length)
            return null;
        socials[pos2] = new Social(instagramId, "Instagram");
        return socials[pos2++];
    }

    public Social addSocialMedia(String title, String id) {
        if (pos2 == socials.length)
            return null;
        socials[pos2] = new Social(id, title);
        return socials[pos2++];
    }

    public ContactInfo[] getInfo() {
        int cap = 0;
        if (name != null) cap++;
        if (phone != null) cap++;
        ContactInfo[] mas = new ContactInfo[cap + pos1 + pos2];
        int pos = 0;
        if (name != null)
            mas[pos++] = new NameContactInfo();
        if (phone != null)
            mas[pos++] = phone;
        for (int i = 0; i < pos1; i++) {
            mas[pos++] = emails[i];
        }
        for (int i = 0; i < pos2; i++) {
            mas[pos++] = socials[i];
        }
        return mas;
    }


    private class NameContactInfo implements ContactInfo {
        @Override
        public String getTitle() {
            return "Name";
        }

        @Override
        public String getValue() {
            return name;
        }
    }

    public static class Email implements ContactInfo {
        String value;

        public Email(String t) {
            value = t;
        }

        @Override
        public String getTitle() {
            return "Email";
        }

        @Override
        public String getValue() {
            return value;
        }
    }

    public static class Social implements ContactInfo {
        String link;
        String title;

        public Social(String l, String t) {
            link = l;
            title = t;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getValue() {
            return link;
        }
    }


}
