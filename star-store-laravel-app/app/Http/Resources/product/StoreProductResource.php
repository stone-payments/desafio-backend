<?php

namespace App\Http\Resources\product;

use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class StoreProductResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            'message' => 'Product created',
            'product' => [
                "id" => $this->id,
                "title" => $this->title,
                "price" => $this->price,
                "zipcode" => $this->zipcode,
                "seller" => $this->seller,
                "thumbnailHd" => $this->thumbnailHd,
                "date" => $this->date->format('d/m/Y'),
            ]
        ];
    }

    public function withResponse(Request $request, JsonResponse $response)
    {
        $response->setStatusCode(201);
    }
}
