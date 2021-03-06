package cz.muni.fi.pa165.mm.sf.facade;

import cz.muni.fi.pa165.mm.api.dto.*;
import cz.muni.fi.pa165.mm.api.facade.AlbumFacade;
import cz.muni.fi.pa165.mm.daolayer.entity.Album;
import cz.muni.fi.pa165.mm.daolayer.entity.Performer;
import cz.muni.fi.pa165.mm.sf.service.AlbumService;
import cz.muni.fi.pa165.mm.sf.service.BeanMappingService;
import cz.muni.fi.pa165.mm.sf.service.PerformerService;
import cz.muni.fi.pa165.mm.sf.service.config.ServiceConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Lukas Suchanek; 433654
 */
@ContextConfiguration(classes = ServiceConfiguration.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class AlbumFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Mock
    private AlbumService albumService;

    @Mock
    private BeanMappingService beanMappingService;

    @Mock
    private PerformerService performerService;

    @Autowired
    @InjectMocks
    private AlbumFacade albumFacade;

    private AlbumDTO albumDTO = new AlbumDTO();
    private Album album = new Album();
    private Performer performer =new Performer();

    @BeforeClass
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @BeforeMethod
    public void  before(){
        PerformerDTO performerDTO = new PerformerDTO();
        performerDTO.setCountry("CZ");
        performerDTO.setName("Performer");

        SongDTO songDTO = new SongDTO();
        songDTO.setReleaseDate(LocalDate.now());
        songDTO.setSongLength(LocalTime.of(0,1,30));
        songDTO.setName("song");

        performer.setId(1L);
        performer.setName("per");
        performer.setCountry("X");

        albumDTO.setId(1L);
        albumDTO.setReleaseDate(LocalDate.now());
        albumDTO.setReleaseDate(LocalDate.now());
        albumDTO.setPerformer(performerDTO);
    }

    @Test
    void testAlbumCreate(){
        AlbumCreateDTO albumCreateDTO = new AlbumCreateDTO();
        albumCreateDTO.setReleaseDate(LocalDate.now());
        albumCreateDTO.setName(albumDTO.getName());
        albumCreateDTO.setReleaseDate(albumDTO.getReleaseDate());

        Mockito.doReturn(album).when(beanMappingService).mapTo(albumCreateDTO, Album.class);
        Mockito.doReturn(performer).when(performerService).findById(any());
//        Mockito.doNothing().when(albumService).create(any());
        Album a = new Album();
        a.setId(1L);
        a.setReleaseDate(LocalDate.now());
        a.setName("album");
        Mockito.doReturn(a).when(albumService).create(any());
        Long id = albumFacade.createAlbum(albumCreateDTO);
        Long test = 1L;
        Assert.assertEquals(id, test);
        Mockito.verify(albumService).create(album);

        Mockito.reset(albumService);
        Mockito.reset(beanMappingService);
    }

    @Test
    void testFindById(){
        Album a = new Album();
        a.setId(1L);
        a.setReleaseDate(LocalDate.now());
        a.setName("album");
        doReturn(a).when(albumService).find(1L);
        doReturn(albumDTO).when(beanMappingService).mapTo(a, AlbumDTO.class);
        AlbumDTO album1 = albumFacade.findById(1L);
        Assert.assertEquals(album1, albumDTO);

        Mockito.reset(albumService);
        Mockito.reset(beanMappingService);
    }
    
    @Test
    void testFindAll(){
        List<AlbumDTO> listDTO = new ArrayList<>();
        listDTO.add(albumDTO);
        List<Album> albums = new ArrayList<>();
        album.setId(1L);
        albums.add(album);
        doReturn(albums).when(albumService).findAll();
        doReturn(listDTO).when(beanMappingService).mapTo(albums ,AlbumDTO.class );

        Assert.assertEquals(1, albumFacade.findAll().size());

        Mockito.reset(albumService);
        Mockito.reset(beanMappingService);
    }

    @Test
    public void testUpdate() {
        when(beanMappingService.mapTo(albumDTO, Album.class)).thenReturn(album);
        doNothing().when(albumService).update(any(Album.class));
        albumFacade.updateAlbum(albumDTO);
        verify(albumService, times(1)).update(album);
        Mockito.reset(albumService);
        Mockito.reset(beanMappingService);
    }

    @Test
    void testDelete(){
        album.setId(1L);
        album.setName("name");
        album.setReleaseDate(LocalDate.now());
        Mockito.doNothing().when(albumService).delete(any(Album.class));
        albumFacade.deleteAlbum(1L);
        verify(albumService).delete(any(Album.class));
        Mockito.reset(albumService);
        Mockito.reset(beanMappingService);
    }

}
