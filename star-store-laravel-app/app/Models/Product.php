<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Product extends Model
{
    use HasFactory;

    protected $fillable = ['title', 'price', 'zipcode', 'seller', 'thumbnailHd', 'date'];

    protected $casts = [
        'date' => 'datetime:d/m/Y'
    ];
}
