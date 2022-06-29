package com.quiztest.quiztest.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryResponse {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class Data {

        @SerializedName("data")
        @Expose
        private List<History> data = null;
        @SerializedName("pagination")
        @Expose
        private Pagination pagination;

        public List<History> getData() {
            return data;
        }

        public void setData(List<History> data) {
            this.data = data;
        }

        public Pagination getPagination() {
            return pagination;
        }

        public void setPagination(Pagination pagination) {
            this.pagination = pagination;
        }

    }

    public static class History {

        @SerializedName("answer_correct")
        @Expose
        private Integer answerCorrect;
        @SerializedName("total_question")
        @Expose
        private Integer totalQuestion;
        @SerializedName("topic_name")
        @Expose
        private String topicName;
        @SerializedName("created_time")
        @Expose
        private String createdTime;
        @SerializedName("id")
        @Expose
        private Integer id;

        public Integer getAnswerCorrect() {
            return answerCorrect;
        }

        public void setAnswerCorrect(Integer answerCorrect) {
            this.answerCorrect = answerCorrect;
        }

        public Integer getTotalQuestion() {
            return totalQuestion;
        }

        public void setTotalQuestion(Integer totalQuestion) {
            this.totalQuestion = totalQuestion;
        }

        public String getTopicName() {
            return topicName;
        }

        public void setTopicName(String topicName) {
            this.topicName = topicName;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }

    public static class Pagination {

        @SerializedName("total")
        @Expose
        private Integer total;
        @SerializedName("count")
        @Expose
        private Integer count;
        @SerializedName("per_page")
        @Expose
        private Integer perPage;
        @SerializedName("current_page")
        @Expose
        private Integer currentPage;
        @SerializedName("total_pages")
        @Expose
        private Integer totalPages;

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }

        public Integer getPerPage() {
            return perPage;
        }

        public void setPerPage(Integer perPage) {
            this.perPage = perPage;
        }

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }
    }
}
