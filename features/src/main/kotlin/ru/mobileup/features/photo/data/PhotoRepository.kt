package ru.mobileup.features.photo.data

import me.aartikov.replica.keyed.KeyedReplica
import me.aartikov.replica.single.Replica
import ru.mobileup.features.photo.domain.Photo

interface PhotoRepository {

    val photosBySearchQueryReplica: KeyedReplica<String, List<Photo>>

    val randomPhotosReplica: Replica<List<Photo>>
}