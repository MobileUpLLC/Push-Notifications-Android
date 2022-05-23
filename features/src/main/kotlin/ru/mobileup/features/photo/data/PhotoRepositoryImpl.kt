package ru.mobileup.features.photo.data

import me.aartikov.replica.client.ReplicaClient
import me.aartikov.replica.keyed.KeyedPhysicalReplica
import me.aartikov.replica.single.Replica
import me.aartikov.replica.single.ReplicaSettings
import ru.mobileup.features.photo.data.dto.toDomain
import ru.mobileup.features.photo.domain.Photo
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds

class PhotoRepositoryImpl(
    replicaClient: ReplicaClient,
    api: PhotoApi
) : PhotoRepository {

    override val photosBySearchQueryReplica: KeyedPhysicalReplica<String, List<Photo>> =
        replicaClient.createKeyedReplica(
            name = "photosBySearchQuery",
            childName = { searchQuery -> "searchQuery = $searchQuery" },
            childSettings = {
                ReplicaSettings(
                    staleTime = 10.seconds,
                    clearTime = 60.seconds
                )
            }
        ) { searchQuery ->
            if (searchQuery.isEmpty()) emptyList() else api.getPhotos(searchQuery).toDomain()
        }

    override val randomPhotosReplica: Replica<List<Photo>> =
        replicaClient.createReplica(
            name = "randomPhotos",
            settings = ReplicaSettings(
                staleTime = 1.hours,
                clearTime = 2.hours
            )
        ) {
            api.getRandomPhotos().map { it.toDomain() }
        }
}