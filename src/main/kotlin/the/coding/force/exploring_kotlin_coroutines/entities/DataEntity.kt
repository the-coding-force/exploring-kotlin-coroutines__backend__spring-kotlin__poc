package the.coding.force.exploring_kotlin_coroutines.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "DATA")
data class DataEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDT_DATA")
    val id: Long? = null,

    @Column(name = "DES_STATUS")
    var status: String,
)
