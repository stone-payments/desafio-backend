<?php

namespace App\Http\Requests\product;

use Illuminate\Foundation\Http\FormRequest;

class StoreProductRequest extends FormRequest
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
            'title' => 'required|string|min:1',
            'price' => 'required|integer|min:1',
            'zipcode' => 'required|string',
            'seller' => 'required|string',
            'thumbnailHd' => 'required|string',
            'date' => 'required|date_format:d/m/Y'
        ];
    }
}
