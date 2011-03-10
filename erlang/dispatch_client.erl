#!/usr/bin/env escript

main([]) ->
  usage();
main(Args) ->
  Results = dispatch_lib:verify_emails(Args),
  lists:foreach(fun(Result) ->
        case Result of
          {result, Email, valid} ->
            io:format("~s: valid~n", [Email]);
          {result, Email, invalid, _} ->
            io:format("~s: invalid~n", [Email])
        end
    end, Results).

usage() ->
  io:format("usage: dispatch_client <email> [email] ...~n").
