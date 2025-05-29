package com.pro.stagelink.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_actor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Actor {
    @Id
    @Column(name = "actor_no")
    private int actorNo;

    @Column(name = "actor_image")
    private String actorImage;

    @Column(name = "actor_name")
    private String actorName;

    @Column(name = "actor_profile")
    private String actorProfile;

    public void changeActorImage(String actorImage) {
        this.actorImage = actorImage;
    }

    public void changeActorName(String actorName) {
        this.actorName = actorName;
    }

    public void changeActorProfile(String actorProfile) {
        this.actorProfile = actorProfile; // ✅ 수정된 부분
    }
}
