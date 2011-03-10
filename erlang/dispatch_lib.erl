-module(dispatch_lib).
-author('jd@chqit.com').
-export([verify_emails/1]).

-define(DISPATCH_URL, "http://v1.dispatch.to/").
-define(API_KEY, "0123456789abcdef").

fetch_and_decode(Url, PostData) ->
  inets:start(),
  Result = http:request(post, {
      Url,
      [],
      "application/x-www-form-urlencoded",
      PostData
    },
    [], []),
  {ok, {{_, 200, _}, _Header, Data}} = Result,
  mochijson2:decode(Data).

urlencode(E) ->
  edoc_lib:escape_uri(E).

join([], _) -> [];
join([List|Lists], Separator) ->
  lists:flatten([urlencode(List) | [[Separator,urlencode(Next)] || Next <- Lists]]).

create_post_data(Emails) ->
  "api_key=" ++ ?API_KEY ++ "&email=" ++ join(Emails, "&email=").

extract_results(JsonStruct) ->
  {struct, [{<<"results">>, Results}]} = JsonStruct,
  lists:map(fun(E) ->
        case E of
          {struct, [{<<"email">>, Email}, {<<"result">>, <<"valid">>}]} ->
            {result, Email, valid};
          {struct, [{<<"email">>, Email}, {<<"result">>, <<"invalid">>}, {<<"errors">>, Errors}]} ->
            {result, Email, invalid, Errors}
        end
    end, Results).

verify_emails(Emails) ->
  PostData = create_post_data(Emails),
  JsonStruct = fetch_and_decode(?DISPATCH_URL, PostData),
  
  Results = extract_results(JsonStruct),

  Results.

