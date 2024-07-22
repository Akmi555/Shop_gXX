package de.ait_tr.gxx_shop.exception_handling;
/*
@date 22.07.2024
@author Sergey Bugaienko
*/

import java.util.List;
import java.util.Objects;

public class ValidationResponse {
    private List<String> messages;

    public ValidationResponse(List<String> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "Response: messages - " + messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ValidationResponse that = (ValidationResponse) o;
        return Objects.equals(messages, that.messages);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(messages);
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }
}
