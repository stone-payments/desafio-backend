<?php

namespace App\Http\Controllers;

use App\Http\Requests\auth\AuthLoginRequest;
use App\Http\Requests\auth\AuthRegisterRequest;
use App\Http\Resources\auth\AuthLoginResource;
use App\Http\Resources\auth\AuthLogoutResource;
use App\Http\Resources\auth\AuthRegisterResource;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class AuthController extends Controller
{

    function register(AuthRegisterRequest $request)
    {
        $validated = $request->validated();

        $user = User::create($validated);

        return AuthRegisterResource::make($user)->response()->setStatusCode(201);
    }

    function login(AuthLoginRequest $request)
    {
        $validated = $request->validated();

        $user = User::where('email', $validated['email'])->first();

        if (!$user || !Hash::check($validated['password'], $user->password))
            return response()->json(['message' => 'Invalid credentials'], 401);

        return AuthLoginResource::make($user)->response()->setStatusCode(200);
    }

    function logout(Request $request)
    {
        $user = $request->user();
        $user->currentAccessToken()->delete();
        return AuthLogoutResource::make($user)->response()->setStatusCode(200);
    }
}
