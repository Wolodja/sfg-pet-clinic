package guru.springframework.sfgpetclinic.services.map;

import guru.springframework.sfgpetclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerMapServiceTest {

    final Long ownerId = 1L;
    final String lastName = "Johns";
    OwnerMapService ownerMapService;

    @BeforeEach
    void setUp() {
        ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
        Owner owner = new Owner();
        owner.setId(ownerId);
        owner.setLastName(lastName);
        ownerMapService.save(owner);
    }

    @Test
    void findAll() {
        Set<Owner> ownerSet = ownerMapService.findAll();

        assertEquals(1, ownerSet.size());
    }

    @Test
    void deleteById() {
        ownerMapService.deleteById(ownerId);

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void delete() {
        ownerMapService.delete(ownerMapService.findById(ownerId));

        assertEquals(0, ownerMapService.findAll().size());
    }

    @Test
    void saveOwnerWithId() {
        Owner owner2 = new Owner();
        owner2.setId(2L);

        Owner savedOwner = ownerMapService.save(owner2);

        assertEquals(owner2.getId(), savedOwner.getId());
    }

    @Test
    void saveOwnerWithoutId(){
        Owner savedOwner = ownerMapService.save(new Owner());
        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void findById() {
        Owner owner = ownerMapService.findById(ownerId);

        assertEquals(ownerId, owner.getId());
    }

    @Test
    void findByLastName() {
        Owner johns = ownerMapService.findByLastName(lastName);

        assertNotNull(johns);
        assertEquals(ownerId, johns.getId());
        assertEquals(lastName, johns.getLastName());
    }

    @Test
    void findByLastNameNotFound() {
        Owner johns = ownerMapService.findByLastName("foo");
        assertNull(johns);
    }
}