package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class UserGamingAccountTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGamingAccount.class);
        UserGamingAccount userGamingAccount1 = new UserGamingAccount();
        userGamingAccount1.setId(1L);
        UserGamingAccount userGamingAccount2 = new UserGamingAccount();
        userGamingAccount2.setId(userGamingAccount1.getId());
        assertThat(userGamingAccount1).isEqualTo(userGamingAccount2);
        userGamingAccount2.setId(2L);
        assertThat(userGamingAccount1).isNotEqualTo(userGamingAccount2);
        userGamingAccount1.setId(null);
        assertThat(userGamingAccount1).isNotEqualTo(userGamingAccount2);
    }
}
