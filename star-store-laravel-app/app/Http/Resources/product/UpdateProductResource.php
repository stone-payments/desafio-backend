<?php

namespace App\Http\Resources\product;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class UpdateProductResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            'message' => 'Product updated',
            'product' => [
                "id" => $this->id,
                "title" => $this->title,
                "price" => $this->price,
                "zipcode" => $this->zipcode,
                "seller" => $this->seller,
                "thumbnailHd" => $this->thumbnailHd,
                "date" => $this->date->format('d/m/Y'),
                "created_at" => $this->created_at,
                "updated_at" => $this->updated_at,
            ]
        ];
    }

    public function withResponse($request, $response)
    {
        $response->setStatusCode(200);
    }
}
