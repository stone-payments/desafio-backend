<?php

namespace Database\Factories;

use Illuminate\Database\Eloquent\Factories\Factory;

/**
 * @extends \Illuminate\Database\Eloquent\Factories\Factory<\App\Models\CreditCard>
 */
class CreditCardFactory extends Factory
{
    /**
     * Define the model's default state.
     *
     * @return array<string, mixed>
     */
    public function definition(): array
    {
        return [
            'id' => $this->faker->uuid(),
            'card_number' => substr($this->faker->creditCardNumber, -4),
            'value' => $this->faker->randomNumber(4, true),
            'cvv' => $this->faker->randomNumber(3, true),
            'card_holder_name' => $this->faker->name,
            'exp_date' => $this->faker->creditCardExpirationDate,
        ];
    }

}
