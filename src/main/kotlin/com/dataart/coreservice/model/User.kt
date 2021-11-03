package com.dataart.coreservice.model

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "users")
data class User(

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var surname: String,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var password: String,

    var linkAva: String? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    //  owner side
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "users_events",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "event_id")]
    )
    var events: MutableList<Event> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var messages: MutableList<Message> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var photos: MutableList<Photo> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var likeEvents: MutableList<LikeEvent> = mutableListOf()

    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL])
    var likePhotos: MutableList<LikePhoto> = mutableListOf()

    @CreatedDate
    @Column(nullable = false)
    var createdDt: Instant = Instant.now()

    @LastModifiedDate
    var updatedDt: Instant = Instant.now()
}
