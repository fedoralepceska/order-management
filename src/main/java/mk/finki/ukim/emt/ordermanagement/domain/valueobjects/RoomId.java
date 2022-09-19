package mk.finki.ukim.emt.ordermanagement.domain.valueobjects;

import mk.ukim.finki.emt.sharedkernel.domain.base.DomainObjectId;

import javax.persistence.Embeddable;

@Embeddable
public class RoomId extends DomainObjectId {
    private RoomId() {
        super(RoomId.randomId(RoomId.class).getId());
    }

    public RoomId(String uuid) {
        super(uuid);
    }
}
