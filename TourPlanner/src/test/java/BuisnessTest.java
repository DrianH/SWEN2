import com.example.tourplanner.BuisnessLayer.BuisnessLogicFactory;
import com.example.tourplanner.BuisnessLayer.IBuisnessLogic;
import com.example.tourplanner.MVVM.Model.Tour;
import com.example.tourplanner.MVVM.Model.TourLog;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BuisnessTest {

    private static final IBuisnessLogic bl = BuisnessLogicFactory.getBuisnessLogic();

    private static final Tour tour1 = new Tour("1", "tour1", "desc", "Wels", "Linz", "CAR", 0.0f, null, null);
    private static final Tour tour2 = new Tour("2", "tour2", "desc", "Wels", "Grieskirchen", "BUS", 0.0f, null, null);
    private static final Tour tour3 = new Tour("3", "tour3", "asfg", "Grieskirchen", "Wien", "WALKING", 0.0f, null, null);

    private static final TourLog tourLog1 = new TourLog("tl1", tour1, LocalDate.now(), "Comment", "00:00");
    private static final TourLog tourLog2 = new TourLog("tl2", tour2, LocalDate.now(), "Comment", "00:00");
    private static final TourLog tourLog3 = new TourLog("tl3", tour1, LocalDate.now(), "Comment", "00:00");


    @BeforeAll
    public static void beforeAll() {
        bl.addTour(tour1);
        bl.addTour(tour2);

        bl.addTourLog(tour1,tourLog1);
        bl.addTourLog(tour2,tourLog2);
    }


    @AfterAll
    public static void afterAll() {
        bl.deleteTourById(tour1.getId());
        bl.deleteTourImageByName(tour1.getId());

        bl.deleteTourLogById(tour2.getId());
        bl.deleteTourImageByName(tour2.getId());

        bl.deleteTourLogById(tourLog1.getId());
        bl.deleteTourLogById(tourLog2.getId());
    }


    @Test
    public void test_getAllTours() {
        List<Tour> allTours = bl.getAllTours();

        assertNotNull(allTours);
    }


    @Test
    public void test_getTourById() {
        Tour tour = bl.getTourById(tour1.getId());

        assertEquals(tour.getId(), tour1.getId());
    }


    @Test
    public void test_addTour() {
        String result = bl.addTour(tour3);

        assertEquals(result, tour3.getId());
    }


    @Test
    public void test_deleteTour() {
        boolean result = bl.deleteTourById(tour3.getId());

        assertTrue(result);
    }


    @Test
    public void test_updateTour() {
        tour1.setDescription("New Description");
        boolean result = bl.updateTour(tour1);
        assertTrue(result);
    }


    @Test
    public void test_createTourLog() {
        String result = bl.addTourLog( tour1,tourLog3);

        assertEquals(result, tourLog3.getId());
    }


    @Test
    public void test_getLogOfTour() {
        List<TourLog> result = bl.getLogOfTour(tour1);

        assertNotNull(result);
    }


    @Test
    public void test_deleteTourLog() {
        boolean result = bl.deleteTourById(tourLog3.getId());

        assertTrue(result);
    }


    @Test
    public void test_updateTourLog() {
        tourLog1.setCommentary("New Comment");
        boolean result = bl.updateTourLog(tourLog1);

        assertTrue(result);
    }


    @Test
    public void test_requestTourDetails() {
        ArrayList<String> result = bl.getTourDetails(tour1);

        assertNotNull(result);
    }


    @Test
    public void test_requestTourMap() {
        String result = bl.getTourMap(tour1);

        assertNotNull(result);
    }


    @Test
    public void test_getTourImage() {
        File file = bl.getTourImage(tour1.getId());

        assertTrue(file.exists());
    }


    @Test
    public void test_deleteTourImage() {
        boolean result = bl.deleteTourImageByName(tour3.getId());

        assertTrue(result);
    }


    @Test
    public void test_getAllTourLogs() {
        List<TourLog> result = bl.getAllTourLogs();

        assertNotNull(result);
    }
}
