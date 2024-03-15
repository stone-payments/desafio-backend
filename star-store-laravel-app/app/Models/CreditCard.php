<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Concerns\HasUuids;

class CreditCard extends Model
{
    use HasFactory, HasUuids;


    protected $fillable = ['card_number', 'cvv', 'value','card_holder_name', 'exp_date'];

    public function transactions()
    {
        return $this->hasMany(Transaction::class);
    }

}
