package the.coding.force.exploring_kotlin_coroutines.repository

import org.springframework.data.jpa.repository.JpaRepository
import the.coding.force.exploring_kotlin_coroutines.entities.DataEntity

interface DataRepository : JpaRepository<DataEntity, Long>
