<?php

namespace App\Http\Resources\transaction;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class StoreTransactionResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            "message" => "Transaction completed successfully",
            "transaction" => [
                'purchase_id' => $this->resource->id,
                'client_id' => $this->resource->user_id,
                'value' => $this->resource->total_to_pay,
                'date' => $this->resource->created_at->format('d/m/Y'),
                'card_number' => '**** **** **** ' . $this->resource->creditCard->card_number
            ]
        ];
    }
}
