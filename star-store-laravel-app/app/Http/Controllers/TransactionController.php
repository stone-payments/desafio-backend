<?php

namespace App\Http\Controllers;

use App\Models\CreditCard;
use App\Models\Transaction;
use App\Models\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Validator;

class TransactionController extends Controller
{
    public function index()
    {
        return Transaction::all();
    }

    public function show(string $id)
    {
        $transaction = Transaction::find($id);

        if (!$transaction) {
            return response()->json(['message' => 'Transaction not found'], 404);
        }

        return $transaction;
    }

    public function store(Request $request)
    {
        $validator = Validator::make($request->all(), [
            'client_id' => 'required|uuid|exists:users,id',
            'client_name' => 'required|string',
            'total_to_pay' => 'required|numeric|min:0',
            'credit_card.card_number' => 'required|numeric|digits:16',
            'credit_card.value' => 'required|numeric|min:0',
            'credit_card.cvv' => 'required|numeric|digits:3',
            'credit_card.card_holder_name' => 'required|string',
            'credit_card.exp_date' => 'required|date_format:m/y'
        ]);

        if ($validator->fails()) {
            return response()->json($validator->errors(), 422);
        }

        $validated = $validator->validated();

        $client = User::find($validated['client_id']);

        if (!$client)
            return response()->json(['message' => 'Client not found'], 404);

        $creditCard = DB::table('credit_cards')
            ->where('card_number', substr($validated['credit_card']['card_number'], -4))
            ->where('card_holder_name', $validated['credit_card']['card_holder_name'])
            ->where('exp_date', $validated['credit_card']['exp_date'])
            ->where('cvv', $validated['credit_card']['cvv'])
            ->first();

        if (!$creditCard) {
            $creditCard = CreditCard::create(
                [
                    'card_number' => substr($validated['credit_card']['card_number'], -4), //last four digits (1234123412341234 -> 1234
                    'value' => $validated['credit_card']['value'],
                    'cvv' => $validated['credit_card']['cvv'],
                    'card_holder_name' => $validated['credit_card']['card_holder_name'],
                    'exp_date' => $validated['credit_card']['exp_date']
                ]
            );
        }

        $transaction = Transaction::create(
            [
                'user_id' => $client->id,
                'credit_card_id' => $creditCard->id,
                'total_to_pay' => $validated['total_to_pay']
            ]
        );

        return response()->json($transaction, 201);

    }
}
