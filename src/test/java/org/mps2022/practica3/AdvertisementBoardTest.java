package org.mps2022.practica3;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class AdvertisementBoardTest {

  @Mock
  AdvertiserDatabase advDatabMock;

  @Mock
  PaymentDatabase paymDatabMock;

  public AdvertisementBoard board;

  @BeforeEach
  public void setup() {
    board = new AdvertisementBoard();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void ABoardHasAnAdvertisementWhenItIsCreated() {
    int obtainedValue = board.numberOfPublishedAdvertisements();
    assertEquals(1,obtainedValue);
  }

  @Test
  public void PublishAnAdvertisementByTheCompanyIncreasesTheNumberOfAdvertisementsByOne() {

    int expectedValue = board.numberOfPublishedAdvertisements()+1;
    Advertisement advertisement = new Advertisement("Anuncio de The Company","soy un anuncio", "THE Company");
    board.publish(advertisement,advDatabMock,paymDatabMock);
    int obtainedValue = board.numberOfPublishedAdvertisements();
    assertEquals(expectedValue, obtainedValue);
  }

  @Test
  public void WhenAnAdvertiserHasNoFoundsTheAdvertisementIsNotPublished() {
    Advertisement advertisement = new Advertisement("Chapuzas a domicilio","Destrozamos tu casa", "Pepe Gotera y Otilio");

    when(advDatabMock.findAdviser(advertisement.advertiser)).thenReturn(true);
    when(paymDatabMock.advertiserHasFunds(advertisement.advertiser)).thenReturn(false);

    int expectedValue = board.numberOfPublishedAdvertisements();

    board.publish(advertisement,advDatabMock,paymDatabMock);
    int obtainedValue = board.numberOfPublishedAdvertisements();
    assertEquals(expectedValue, obtainedValue);
  }

  @Test
  public void AnAdvertisementIsPublishedIfTheAdvertiserIsRegisteredAndHasFunds() {

    Advertisement advertisement = new Advertisement("Anuncio de Robin Robot","Me llaman Bender", "Robin Robot");

    when(advDatabMock.findAdviser(advertisement.advertiser)).thenReturn(true);
    when(paymDatabMock.advertiserHasFunds(advertisement.advertiser)).thenReturn(true);

    int expectedValue = board.numberOfPublishedAdvertisements()+1;

    board.publish(advertisement,advDatabMock,paymDatabMock);
    int obtainedValue = board.numberOfPublishedAdvertisements();
    assertEquals(expectedValue, obtainedValue);

    verify(paymDatabMock, times(1)).advertisementPublished(anyString());
  }

  @Test
  public void WhenTheOwnerMakesTwoPublicationsAndTheFirstOneIsDeletedItIsNotInTheBoard() {
    Advertisement advertisement1 = new Advertisement("Anuncio de The Company 1","soy el primer anuncio", "THE Company");
    Advertisement advertisement2 = new Advertisement("Anuncio de The Company 2","soy el segundo anuncio", "THE Company");

    board.publish(advertisement1,advDatabMock,paymDatabMock);
    board.publish(advertisement2,advDatabMock,paymDatabMock);
    board.deleteAdvertisement("Anuncio de The Company 1", "THE Company");

    assertNull(board.findByTitle("Anuncio de The Company 1"));
  }

  @Test
  public void AnExistingAdvertisementIsNotPublished() {
    Advertisement advertisement1 = new Advertisement("Anuncio de Robin Robot","Me llaman Bender", "Robin Robot");
    Advertisement advertisement2 = new Advertisement("Anuncio de Robin Robot","Me llaman Bender", "Robin Robot");

    when(advDatabMock.findAdviser(advertisement1.advertiser)).thenReturn(true);
    when(paymDatabMock.advertiserHasFunds(advertisement1.advertiser)).thenReturn(true);

    board.publish(advertisement1,advDatabMock,paymDatabMock); // Esta debería ser la única invocación de advertisementPublished()
    int expectedValue = board.numberOfPublishedAdvertisements();

    when(advDatabMock.findAdviser(advertisement2.advertiser)).thenReturn(true);
    when(paymDatabMock.advertiserHasFunds(advertisement2.advertiser)).thenReturn(true);

    board.publish(advertisement2,advDatabMock,paymDatabMock);
    int obtainedValue = board.numberOfPublishedAdvertisements();

    assertEquals(expectedValue, obtainedValue);

    // Verificamos que habiendo tratado de insertar dos anuncios iguales seguidos, sólo se publica  el primero
    verify(paymDatabMock, times(1)).advertisementPublished(anyString());
  }

  @Test
  public void AnExceptionIsRaisedIfTheBoardIsFullAndANewAdvertisementIsPublished() {}
}