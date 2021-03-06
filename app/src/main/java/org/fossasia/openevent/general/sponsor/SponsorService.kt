package org.fossasia.openevent.general.sponsor

import androidx.lifecycle.LiveData
import io.reactivex.Single

class SponsorService(
    private val sponsorApi: SponsorApi,
    private val sponsorDao: SponsorDao,
    private val sponsorWithEventDao: SponsorWithEventDao
) {
    fun fetchSponsorsWithEvent(id: Long): Single<List<Sponsor>> {
        return sponsorApi.getSponsorWithEvent(id).doOnSuccess { sponsors ->
            sponsors.forEach { sponsor ->
                sponsorDao.insertSponsor(sponsor)
                sponsorWithEventDao.insert(SponsorWithEvent(id, sponsor.id))
            }
        }
    }

    fun fetchSponsorsFromDb(id: Long): LiveData<List<Sponsor>> =
        sponsorWithEventDao.getSponsorWithEventId(id)
}
