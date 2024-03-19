<?php

namespace App\Http\Resources\transaction;

use Illuminate\Database\Eloquent\Collection;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\ResourceCollection;
use Illuminate\Support\Collection as SupportCollection;


class IndexTransactionResourceCollection extends ResourceCollection
{
    /**
     * Transform the resource collection into an array.
     *
     * @return array<int|string, mixed>
     */
    public function toArray(Request $request): SupportCollection
    {
        return $this->collection;
    }

    public function withResponse(Request $request, JsonResponse $response)
    {
        $response->setStatusCode(200);
    }
}
