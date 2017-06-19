package com.sugar.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 * Created by hongseongmin on 2017. 6. 18..
 */
@Entity
public class Question {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;
    
    private String title;

    @Lob
    private String contents;
    private LocalDateTime createdDate;

    public Question() {}
    public Question(User writer, String title, String contents) {
        super();
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createdDate = LocalDateTime.now();
    }
    
    public String getFormattedCreatedDate() {
    	if (createdDate == null) {
    		return "";
    	}
    	return createdDate.format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss"));
    }
	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
	public boolean isSameUser(User logindUser) {
		return this.writer.equals(logindUser);
	}
}
