<?php

use App\Http\Controllers\AuthController;
use App\Http\Controllers\ProductController;
use App\Http\Controllers\TransactionController;
use Illuminate\Support\Facades\Route;

Route::prefix('auth')->group(function () {
    Route::post('/register', [AuthController::class, 'register'])->name('register');

    Route::post('/login', [AuthController::class, 'login'])->name('login');

    Route::middleware('auth:sanctum')->group(function () {
        Route::post('/logout', [AuthController::class, 'logout'])->name('logout');
    });
});

Route::prefix('starstore')->group(function () {
    Route::prefix('products')->group(function () {

        Route::get('/', [ProductController::class, 'index'])->name('get.products');

        Route::get('/{id}', [ProductController::class, 'show'])->name('get.product');

        Route::post('/', [ProductController::class, 'store'])->name('create.product');

        Route::match(['put', 'patch'], '/{id}', [ProductController::class, 'update'])->name('update.product');

        Route::delete('/{id}', [ProductController::class, 'destroy'])->name('delete.product');
    });

    Route::prefix('buy')->group(function () {
        Route::post('/', [TransactionController::class, 'store'])->name('buy.product')->middleware('auth:sanctum');
    });

    Route::middleware('auth:sanctum')->group(function () {
        Route::prefix('history')->group(function () {
            Route::get('/', [TransactionController::class, 'index'])->name('get.history');

            Route::get('/{id}', [TransactionController::class, 'showUserTransactions'])->name('show.user.history');
        });
    });
});
