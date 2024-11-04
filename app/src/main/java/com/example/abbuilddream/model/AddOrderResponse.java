package com.example.abbuilddream.model;

public class AddOrderResponse {

        private Result result;
        private int id;
        private String exception;
        private int status;
        private boolean isCanceled;
        private boolean isCompleted;
        private boolean isCompletedSuccessfully;
        private int creationOptions;
        private Object asyncState;
        private boolean isFaulted;

        // Constructor
        public AddOrderResponse() {
        }


        // Getters and Setters
        public Result getResult() {
            return result;
        }

        public void setResult(Result result) {
            this.result = result;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getException() {
            return exception;
        }

        public void setException(String exception) {
            this.exception = exception;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public boolean isCanceled() {
            return isCanceled;
        }

        public void setCanceled(boolean canceled) {
            isCanceled = canceled;
        }

        public boolean isCompleted() {
            return isCompleted;
        }

        public void setCompleted(boolean completed) {
            isCompleted = completed;
        }

        public boolean isCompletedSuccessfully() {
            return isCompletedSuccessfully;
        }

        public void setCompletedSuccessfully(boolean completedSuccessfully) {
            isCompletedSuccessfully = completedSuccessfully;
        }

        public int getCreationOptions() {
            return creationOptions;
        }

        public void setCreationOptions(int creationOptions) {
            this.creationOptions = creationOptions;
        }

        public Object getAsyncState() {
            return asyncState;
        }

        public void setAsyncState(Object asyncState) {
            this.asyncState = asyncState;
        }

        public boolean isFaulted() {
            return isFaulted;
        }

        public void setFaulted(boolean faulted) {
            isFaulted = faulted;
        }

        // Inner class for Result
        public static class Result {
            private String message;
            private int code;

            public String getMessage() {
                return message;
            }

            public void setMessage(String message) {
                this.message = message;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }
        }
    }

