<?php

namespace App\Http\Resources\auth;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class AuthLoginResource extends JsonResource
{
    public static $wrap = 'user';
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            'message' => 'Logged in successfully',
            'user' => [
                'name' => $this->name,
                'email' => $this->email,
                'token' => $this->createToken('authToken')->plainTextToken
            ]
        ];
    }
}
