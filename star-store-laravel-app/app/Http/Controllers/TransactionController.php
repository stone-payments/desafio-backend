<?php

namespace App\Http\Controllers;

use App\Http\Requests\transaction\StoreTransactionRequest;
use App\Http\Resources\ErrorResource;
use App\Http\Resources\transaction\IndexTransactionResource;
use App\Http\Resources\transaction\IndexTransactionResourceCollection;
use App\Http\Resources\transaction\StoreTransactionResource;
use App\Models\CreditCard;
use App\Models\Transaction;
use App\Models\User;

use Illuminate\Support\Facades\DB;


class TransactionController extends Controller
{
    public function index()
    {
        $transactions = Transaction::all();
        return new IndexTransactionResourceCollection($transactions);
    }

    public function show(string $id)
    {
        $transaction = Transaction::find($id);

        if (!$transaction) {
            return new ErrorResource(['message' => 'Transaction not found', 'statusCode' => 404]);
        }

        return IndexTransactionResource::make($transaction);
    }

    public function showUserTransactions(string $id)
    {
        $transactions = Transaction::where('user_id', $id)->get();

        if ($transactions->isEmpty()) {
            return new ErrorResource(['message' => 'User has no transactions', 'statusCode' => 404]);
        }

        return new IndexTransactionResourceCollection($transactions);
    }

    public function store(StoreTransactionRequest $request)
    {
        $validated = $request->validated();

        $client = User::find($validated['client_id']);

        if (!$client)
            return new ErrorResource(['message' => 'Client not found', 'statusCode' => 404]);

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

        return new StoreTransactionResource($transaction);
    }
}
