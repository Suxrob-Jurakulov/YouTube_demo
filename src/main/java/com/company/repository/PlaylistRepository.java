package com.company.repository;

import com.company.dto.playlist.PlaylistInfoDTO;
import com.company.dto.playlist.PlaylistStatusDTO;
import com.company.entity.PlaylistEntity;
import com.company.mapper.PlaylistInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends PagingAndSortingRepository<PlaylistEntity, String> {

    @Transactional
    @Modifying
    @Query(value = "update PlaylistEntity set status = ?1 where id = ?2")
    void updateStatus(PlaylistStatusDTO dto, String id);


//    @Query(value = "select new com.company.dto.playlist.PlaylistInfoDTO(id, name, description, status, orderNum, channel) from PlaylistEntity " )
//    Page<PlaylistInfoDTO> getAllListForAdmin(Pageable pageable);

    @Query(value = "select new com.company.dto.playlist.PlaylistInfoDTO(id, name, description, status, orderNum, channel) from PlaylistEntity " )
    PlaylistInfoDTO getAllListForAdmin();

    @Transactional
    @Modifying
    @Query("update PlaylistEntity set visible = false where id = ?1")
    void deletePlaylist(String id);

    @Query(value = "select p.id as id, p.name as name, p.description as description, p.status as status, p.order_num as orders," +
            "         c.id as channelId, c.name as channelName, c.photo_id as channelAttachId, pr.id as profileId," +
            "     pr.name as profileName, pr.photo_id as profileAttachId " +
            "     from playlist p " +
            "     join channel c on p.channel_id = c.id " +
            "     join profile pr on c.profile_id = pr.id", nativeQuery = true)
    Page<PlaylistInfo> pagination(Pageable pageable);


    @Query(value = "from PlaylistEntity p join VideoEntity v on v.playlistId = p.id where v.id = ?1")
    Optional<PlaylistEntity> getPlaylistByVideoId(String id);
}

