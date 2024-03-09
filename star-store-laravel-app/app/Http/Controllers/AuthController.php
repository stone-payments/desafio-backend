<?php

namespace App\Http\Controllers;

use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Hash;

class AuthController extends Controller
{

    function register()
    {
        $validated = request()->validate([
            'name' => 'required',
            'email' => 'required|email|unique:users,email',
            'password' => 'required'
        ]);

        $user = User::create($validated);

        return response()->json([$user], 201);
    }

    function login()
    {
        $validated = request()->validate([
            'email' => 'required|email',
            'password' => 'required'
        ]);

        $user = User::where('email', $validated['email'])->first();

        if (!$user || !Hash::check($validated['password'], $user->password))
            return response()->json(['message' => 'Invalid credentials'], 401);

        return response()->json(
            [
                'user' => $user,
                'token' => $user->createToken('auth_token')->plainTextToken
            ],
            200
        );
    }

    function logout(Request $request)
    {
        $request->user()->currentAccessToken()->delete();
        return response()->json(['message' => 'Logged out'], 200);
    }
}
