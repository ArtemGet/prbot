package io.github.artemget.prbot.config;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link EJsonArr}.
 *
 * @since 0.0.1
 */
class EJsonArrTest {

    @Test
    void throwsAtWrongAttrType() {
        Assertions.assertThrows(
            EntryException.class,
            () -> new EJsonArr(
                Json.createObjectBuilder()
                    .add("name", "not json array")
                    .build(),
                "name"
            ).value(),
            "EJsonArr did not rethrow at wrong field type"
        );
    }

    @Test
    void throwsAtNullValue() {
        Assertions.assertThrows(
            EntryException.class,
            () -> new EJsonArr(
                Json.createObjectBuilder().build(),
                "name"
            ).value(),
            "EJsonArr did not rethrow at null"
        );
    }

    @Test
    void returnsObject() throws EntryException {
        final JsonObject object = Json.createObjectBuilder()
            .add("users", Json.createArrayBuilder().build())
            .build();
        MatcherAssert.assertThat(
            "EJsonArr did not return expected value",
            new EJsonArr(
                object,
                "users"
            ).value(),
            Matchers.equalTo(object.getJsonArray("users"))
        );
    }
}
