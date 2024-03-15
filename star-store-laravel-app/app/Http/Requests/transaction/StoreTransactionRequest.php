<?php

namespace App\Http\Requests\transaction;

use Illuminate\Foundation\Http\FormRequest;

class StoreTransactionRequest extends FormRequest
{
    /**
     * Determine if the user is authorized to make this request.
     */
    public function authorize(): bool
    {
        return true;
    }

    /**
     * Get the validation rules that apply to the request.
     *
     * @return array<string, \Illuminate\Contracts\Validation\ValidationRule|array<mixed>|string>
     */
    public function rules(): array
    {
        return [
            'client_id' => 'required|uuid|exists:users,id',
            'client_name' => 'required|string',
            'total_to_pay' => 'required|numeric|min:0',
            'credit_card.card_number' => 'required|numeric|digits:16',
            'credit_card.value' => 'required|numeric|min:0',
            'credit_card.cvv' => 'required|numeric|digits:3',
            'credit_card.card_holder_name' => 'required|string',
            'credit_card.exp_date' => 'required|date_format:m/y'
        ];
    }
}
