package guru.qa.rococo.api;

import com.fasterxml.jackson.databind.JsonNode;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Auth {

  @GET("/oauth2/authorize")
  Call<Void> authorize(
      @Query("response_type") String responseType,
      @Query("client_id") String clientId,
      @Query("scope") String scope,
      @Query(value = "redirect_uri", encoded = true) String redirectUri,
      @Query("code_challenge") String codeChallenge,
      @Query("code_challenge_method") String codeChallengeMethod
  );

  @POST("/login")
  @FormUrlEncoded
  Call<Void> login(
      @Field("username") String username,
      @Field("password") String password,
      @Field("_csrf") String csrf
  );

  @POST("oauth2/token")
  @FormUrlEncoded
  Call<JsonNode> token(
      @Field("client_id") String clientId,
      @Field(value = "redirect_uri", encoded = true) String redirectUri,
      @Field("grant_type") String grantType,
      @Field("code") String code,
      @Field("code_verifier") String codeVerifier
  );

  @GET("register")
  Call<Void> requestRegForm();

  @POST("register")
  @FormUrlEncoded
  Call<Void> register(
      @Field("username") String username,
      @Field("password") String password,
      @Field("passwordSubmit") String passwordSubmit,
      @Field("_csrf") String csrf
  );
}
