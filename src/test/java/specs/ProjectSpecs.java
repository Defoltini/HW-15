package specs;

import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;

public class ProjectSpecs {
    public static ResponseSpecification specWithCode200AndLogAll = with()
            .log().all()
            .expect().statusCode(200);

    public static ResponseSpecification specWithCode201AndLogAll = with()
            .log().all()
            .expect().statusCode(201);
    public static ResponseSpecification specWithCode204AndLogAll = with()
            .log().all()
            .expect().statusCode(204);
}
