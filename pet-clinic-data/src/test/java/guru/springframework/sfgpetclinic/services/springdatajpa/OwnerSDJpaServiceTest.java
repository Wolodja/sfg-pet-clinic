package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.repositories.OwnerRepository;
import guru.springframework.sfgpetclinic.repositories.PetRepository;
import guru.springframework.sfgpetclinic.repositories.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OwnerSDJpaServiceTest {

    @Mock
    OwnerRepository ownerRepository;

    @Mock
    PetRepository petRepository;

    @Mock
    PetTypeRepository petTypeRepository;

    @InjectMocks
    OwnerSDJpaService ownerService;

    Owner returnedOwner;

    @BeforeEach
    void setUp() {
        returnedOwner = new Owner();
        returnedOwner.setId(1L);
    }

    @Test
    void findByLastName() {
        String lastName = "Smith";
        returnedOwner.setLastName(lastName);
        when(ownerRepository.findByLastName(any())).thenReturn(returnedOwner);

        Owner smitch = ownerService.findByLastName(lastName);

        assertEquals(lastName, smitch.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {
        Set<Owner> returnedOwners = new HashSet<>();
        Owner otherOWner = new Owner();
        otherOWner.setId(2L);
        returnedOwners.add(returnedOwner);
        returnedOwners.add(otherOWner);

        when(ownerRepository.findAll()).thenReturn(returnedOwners);

        Set<Owner> owners = ownerService.findAll();
        assertNotNull(owners);
        assertEquals(2, owners.size());
    }

    @Test
    void findById() {
        when(ownerRepository.findById(any())).thenReturn(Optional.of(returnedOwner));

        Owner owner = ownerService.findById(1L);

        assertNotNull(owner);
    }

    @Test
    void findByIdNotFound() {
        when(ownerRepository.findById(any())).thenReturn(Optional.empty());

        Owner owner = ownerService.findById(1L);

        assertNull(owner);
    }

    @Test
    void save() {
        when(ownerRepository.save(any())).thenReturn(returnedOwner);

        Owner savedOwner = ownerService.save(returnedOwner);

        assertNotNull(savedOwner);
    }

    @Test
    void delete() {
        ownerService.delete(returnedOwner);

        verify(ownerRepository, times(1)).delete(any());
    }

    @Test
    void deleteById() {
        ownerService.deleteById(returnedOwner.getId());

        verify(ownerRepository, times(1)).deleteById(any());
    }
}