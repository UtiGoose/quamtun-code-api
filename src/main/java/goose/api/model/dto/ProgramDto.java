package goose.api.model.dto;

import goose.api.model.pojo.Options;

import java.util.Date;
import java.util.List;

public class ProgramDto {
    private Integer id;

    private String pyName;

    private String labelTitle;

    private String labelQuestion;

    private String labelTask;

    private String imageCover;

    private String labelFirst;

    private String imageFirst;

    private String labelSecond;

    private String imageSecond;

    private List<Options> options;

    private Integer views;

    private Date createTime;

    private String username;

    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPyName() {
        return pyName;
    }

    public void setPyName(String pyName) {
        this.pyName = pyName;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public String getLabelQuestion() {
        return labelQuestion;
    }

    public void setLabelQuestion(String labelQuestion) {
        this.labelQuestion = labelQuestion;
    }

    public String getImageCover() {
        return imageCover;
    }

    public void setImageCover(String imageCover) {
        this.imageCover = imageCover;
    }

    public String getLabelFirst() {
        return labelFirst;
    }

    public void setLabelFirst(String labelFirst) {
        this.labelFirst = labelFirst;
    }

    public String getImageFirst() {
        return imageFirst;
    }

    public void setImageFirst(String imageFirst) {
        this.imageFirst = imageFirst;
    }

    public String getLabelSecond() {
        return labelSecond;
    }

    public void setLabelSecond(String labelSecond) {
        this.labelSecond = labelSecond;
    }

    public String getImageSecond() {
        return imageSecond;
    }

    public void setImageSecond(String imageSecond) {
        this.imageSecond = imageSecond;
    }

    public List<Options> getOptions() {
        return options;
    }

    public void setOptions(List<Options> options) {
        this.options = options;
    }

    public String getLabelTask() {
        return labelTask;
    }

    public void setLabelTask(String labelTask) {
        this.labelTask = labelTask;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "ProgramDto{" +
                "id=" + id +
                ", pyName='" + pyName + '\'' +
                ", labelTitle='" + labelTitle + '\'' +
                ", labelQuestion='" + labelQuestion + '\'' +
                ", labelTask='" + labelTask + '\'' +
                ", imageCover='" + imageCover + '\'' +
                ", labelFirst='" + labelFirst + '\'' +
                ", imageFirst='" + imageFirst + '\'' +
                ", labelSecond='" + labelSecond + '\'' +
                ", imageSecond='" + imageSecond + '\'' +
                ", options=" + options +
                ", views=" + views +
                ", createTime=" + createTime +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}
