package me.lofienjoyer.quiet.basemodel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String username;

    @OneToOne(mappedBy = "profile")
    @JsonIgnore
    private UserInfo user;

    @OneToMany(mappedBy = "profile")
    @JsonIgnore
    private Set<Post> posts;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "follows",
            joinColumns = @JoinColumn(name = "profile_id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id")
    )
    @JsonIgnore
    private List<Profile> following;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "follows",
            joinColumns = @JoinColumn(name = "followed_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id")
    )
    @JsonIgnore
    private List<Profile> followed;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "likes")
    @JsonIgnore
    private List<Post> likedPosts;

}
