// Copyright 2021 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import com.google.ads.googleads.lib.logging.LoggingInterceptor;
import com.google.ads.googleads.lib.logging.RequestLogger;
import com.google.ads.googleads.v6.services.GoogleAdsServiceClient;
import com.google.ads.googleads.v6.services.GoogleAdsServiceSettings;
import com.google.api.gax.grpc.InstantiatingGrpcChannelProvider;
import com.google.auth.oauth2.UserCredentials;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.grpc.ClientInterceptor;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Issue {

  public static void main(String[] args) throws IOException {
    UserCredentials credentials =
        UserCredentials.newBuilder()
            .setClientId("")
            .setClientSecret("")
            .setRefreshToken("this does not exist")
            .build();

    GoogleAdsServiceSettings settings =
        GoogleAdsServiceSettings.newBuilder()
            .setTransportChannelProvider(
                InstantiatingGrpcChannelProvider.newBuilder()
                    .setInterceptorProvider(
                        () ->
                            ImmutableList.of(
                                new LoggingInterceptor(new RequestLogger(), ImmutableMap.of(), "")))
                    .build())
            .setCredentialsProvider(() -> credentials)
            .build();

    GoogleAdsServiceClient client = GoogleAdsServiceClient.create(settings);

    System.out.println("issuing request");

    client.search("123", "select foo");

    System.out.println("never reached");
  }
}
