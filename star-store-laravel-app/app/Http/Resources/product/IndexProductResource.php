<?php

namespace App\Http\Resources\product;

use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class IndexProductResource extends JsonResource
{
    public static $wrap = null;

    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            "title" => $this->title,
            "price" => $this->price,
            "zipcode" => $this->zipcode,
            "seller" => $this->seller,
            "thumbnailHd" => $this->thumbnailHd,
            "date" => $this->date->format('d/m/Y'),
        ];
    }

    public static function collection($resource)
    {
        return $resource->map(function ($product) {
            return [
                "title" => $product->title,
                "price" => $product->price,
                "zipcode" => $product->zipcode,
                "seller" => $product->seller,
                "thumbnailHd" => $product->thumbnailHd,
                "date" => $product->date->format('d/m/Y'),
            ];
        });
    }

    /**
     * Customize the response for a request.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \Illuminate\Http\JsonResponse  $response
     * @return void
     */
    public function withResponse(Request $request, JsonResponse $response)
    {
        $response->setStatusCode(200);
    }
}
