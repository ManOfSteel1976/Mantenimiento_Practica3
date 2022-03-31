package org.mps2022.practica3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AdvertisementBoardTest {

  public AdvertisementBoard board;

  @Test
  public void ABoardHasAnAdvertisementWhenItIsCreated() {
    board = new AdvertisementBoard();
    int obtainedValue = board.numberOfPublishedAdvertisements();
    assertEquals(1,obtainedValue);
  }

  @Test
  public void PublishAnAdvertisementByTheCompanyIncreasesTheNumberOfAdvertisementsByOne() {
    int expectedValue = board.numberOfPublishedAdvertisements()+1;
    Advertisement advertisement = new Advertisement("Anuncio de The Company","soy un anuncio", "THE Company");
    board.publish(advertisement,null,null);
    int obtainedValue = board.numberOfPublishedAdvertisements();
    assertEquals(expectedValue, obtainedValue);
  }

  @Test
  public void WhenAnAdvertiserHasNoFoundsTheAdvertisementIsNotPublished() {}

  @Test
  public void AnAdvertisementIsPublishedIfTheAdvertiserIsRegisteredAndHasFunds() {}

  @Test
  public void WhenTheOwnerMakesTwoPublicationsAndTheFirstOneIsDeletedItIsNotInTheBoard() {}

  @Test
  public void AnExistingAdvertisementIsNotPublished() {}

  @Test
  public void AnExceptionIsRaisedIfTheBoardIsFullAndANewAdvertisementIsPublished() {}
}