package rocks.zipcode.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import rocks.zipcode.web.rest.TestUtil;

class MessageBoxTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageBox.class);
        MessageBox messageBox1 = new MessageBox();
        messageBox1.setId(1L);
        MessageBox messageBox2 = new MessageBox();
        messageBox2.setId(messageBox1.getId());
        assertThat(messageBox1).isEqualTo(messageBox2);
        messageBox2.setId(2L);
        assertThat(messageBox1).isNotEqualTo(messageBox2);
        messageBox1.setId(null);
        assertThat(messageBox1).isNotEqualTo(messageBox2);
    }
}
