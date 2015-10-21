package com.edgar.assertj.generator;

import org.junit.Test;

import static com.edgar.assertj.generator.Assertions.assertThat;

/**
 * Created by Administrator on 2015/10/21.
 */
public class PlayerTest {

    @Test
    public void testPlayer() {
        Player player = new Player();
        assertThat(player);
//        .hasName("Michael Jordan")
//                .hasTeam("Chicago Bulls")
//                .hasTeamMates("Scottie Pippen", "Tony Kukoc");
    }

}
