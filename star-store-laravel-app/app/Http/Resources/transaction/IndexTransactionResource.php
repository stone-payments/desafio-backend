<?php

namespace App\Http\Resources\transaction;

use Illuminate\Http\Request;
use Illuminate\Http\Resources\Json\JsonResource;

class IndexTransactionResource extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @return array<string, mixed>
     */
    public function toArray(Request $request): array
    {
        return [
            'client_id' => $this->user_id,
            'purchase_id' => $this->id,
            'value' => $this->total_to_pay,
            'date' => $this->created_at->format('d/m/Y'),
            'card_number' => '**** **** **** ' . $this->creditCard->card_number
        ];
    }

}
