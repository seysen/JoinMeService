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
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

@Table(name = "events")
@Entity
data class Event(

    @Column(nullable = false)
    var name: String,

    var description: String,

    var date: Instant,

    var linkAva: String,

    // главная сторона owner
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", referencedColumnName = "id", nullable = false)
    var creatorId: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    // зависимая сторона <--- нельзя через event repo добавить user
    @ManyToMany(mappedBy = "events", cascade = [CascadeType.ALL])
    var users: MutableList<User> = mutableListOf()

    // главная сторона owner
    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(
        name = "events_categories",
        joinColumns = [JoinColumn(name = "event_id")],
        inverseJoinColumns = [JoinColumn(name = "category_id")]
    )
    var categories: MutableList<Category> = mutableListOf()

    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL])
    var messages: MutableList<Message> = mutableListOf()

    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL])
    var photos: MutableList<Photo> = mutableListOf()

    @OneToMany(mappedBy = "event", cascade = [CascadeType.ALL])
    var likeEvents: MutableList<LikeEvent> = mutableListOf()

    @CreatedDate
    @Column(nullable = false)
    var createdDt: Instant = Instant.now()

    @LastModifiedDate
    var updatedDt: Instant = Instant.now()
}
